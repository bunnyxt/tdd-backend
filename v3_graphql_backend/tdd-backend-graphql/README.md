# tdd-backend-graphql

## Introduction

The graphql backend of TianDian Daily.

## Get Started

Step 0: Make sure you have `node` and `npm` installed on your machine. This project is developed and tested on `node v16.13.2 (npm v8.1.2)`. Other versions may also works, but not tested.

Step 1: Clone repository to your machine and change directory to root of this backend.

```
git clone https://github.com/bunnyxt/tdd-backend.git
cd tdd-backend/v3_graphql_backend/tdd-backend-graphql
```

Step 2: Install dependencies.

```
npm install
```

Step 3: Edit config.

Duplicate file `.env.template` and rename the copy to `.env`. Edit `.env` file, replace default config (server running port, DB connection, etc.) with your customed info, then save and exit. Note, once you changed `.env` file, you have to restart the server to apply your change to the running instance. 

Step 4: Start server.

```
npm run start
```

If everything goes well, server will run successfully and you will see `🚀 Server ready at http://localhost:4000/graphql`, where `4000` is the default port. Try visit this URL via browser to use the online query console, or query this graph directly via other client (e.g. curl, Postman, etc.).

## Disclaimer 

- Author: [bunnyxt](https://github.com/bunnyxt)
