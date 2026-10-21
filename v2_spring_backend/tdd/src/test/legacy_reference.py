import pathlib,json,subprocess,re,base64,argparse,sys
parser=argparse.ArgumentParser(description="Generate synthetic PHP 7.0 compatibility responses; never reads conn.php.")
parser.add_argument("--php",default="php")
parser.add_argument("--emit-programs",action="store_true")
options=parser.parse_args()
repository=pathlib.Path(__file__).resolve().parents[4]
root=repository/"v1_php_backend/tdd"
fixture=json.loads((repository/"v2_spring_backend/tdd/src/test/resources/legacy/fixture.json").read_text())
def clean(s):
 s=re.sub(r"<\?php|\?>","",s)
 s=re.sub(r"require_once\([^;]+;","",s)
 return s
common="\n".join(clean(p.read_text()) for p in sorted((root/"model").glob("*.php")))
common+=clean((root/"util/Wrapper.php").read_text())+clean((root/"db/sql_helper.php").read_text())
common+="\n".join(clean(p.read_text()) for p in sorted((root/"db").glob("db_*.php")))
harness="<?php\nnamespace LegacyOracle;\nuse \\PDO;\nini_set('display_errors', 0);\nerror_reporting(0);\nclass FixtureResult {\n    private $rows;\n    function __construct($rows) { $this->rows = $rows; }\n    function fetch($mode) { return array_shift($this->rows); }\n}\nclass FixtureConnection {\n    function query($sql) {\n        global $fixture;\n        preg_match('/from (\\\\w+)/i', $sql, $m);\n        $rows = $fixture['tables'][$m[1]];\n        preg_match_all('/and `(\\\\w+)` (>=|<|=) (\"[^\"]*\"|-?\\\\d+)/', $sql, $conditions, PREG_SET_ORDER);\n        if (substr_count($sql, \"and \") != count($conditions)) return false;\n        foreach ($conditions as $c) {\n            $value = trim($c[3], '\"');\n            $rows = array_values(array_filter($rows, function($row) use($c,$value) {\n                if ($c[2] == '=') return $row[$c[1]] == $value;\n                if ($c[2] == '>=') return $row[$c[1]] >= $value;\n                return $row[$c[1]] < $value;\n            }));\n        }\n        if (strpos($sql, 'order by') !== false) usort($rows, function($a,$b) { return $b['id'] - $a['id']; });\n        if (preg_match('/limit (\\\\d+)/', $sql, $m)) $rows = array_slice($rows, 0, intval($m[1]));\n        return new FixtureResult($rows);\n    }\n}\nfunction file_get_contents($url) {\n    global $fixture;\n    $key = parse_url($url, PHP_URL_QUERY);\n    return array_key_exists($key, $fixture['upstream']) ? $fixture['upstream'][$key] : $fixture['upstream']['default'];\n}\n"
programs=[]
for case in fixture["cases"]:
 endpoint=clean((root/(case["route"]+".php")).read_text())
 if case["route"]=="sprint_video_record":
  endpoint=endpoint.replace("// db operation", 'if ($aid <= 0) { echo(\'{"status":400,"meta":{"message":"必须提供aid并且aid为正整数"},"data":null}\'); die(); }\n// db operation')
 setup="$fixture=json_decode(base64_decode('"+base64.b64encode(json.dumps(fixture).encode()).decode()+"'),true);\n$conn=new FixtureConnection();\n"
 setup+="parse_str(base64_decode('"+base64.b64encode(case["query"].encode()).decode()+"'), $_GET);\n"
 programs.append(harness+common+setup+endpoint)
if options.emit_programs:
 print(json.dumps(programs))
 sys.exit(0)
results=[]
for program in programs:
 r=subprocess.run([options.php],input=program.encode(),stdout=subprocess.PIPE,stderr=subprocess.PIPE,check=True)
 results.append({"body":r.stdout.decode(),"exit":r.returncode})
for c,r in zip(fixture["cases"],results): c.update(expected=r["body"],php_exit=r["exit"])
path=(repository/"v2_spring_backend/tdd/src/test/resources/legacy/fixture.json")
path.write_text(json.dumps(fixture,ensure_ascii=False,indent=2)+"\n")
print("PHP fixture cases:",len(results),"nonzero exits:",sum(r["exit"]!=0 for r in results))
for c in fixture["cases"]:
 if c["route"]=="sprint_daily_record" and c["query"]=="date=20260102": print("orphan daily:",c["expected"])
