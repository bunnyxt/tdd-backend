const { DataTypes, Model } = require('sequelize');
const db = require('../db');

class Video extends Model {}

Video.init({
  id: {
    type: DataTypes.BIGINT,
    allowNull: false,
    primaryKey: true,
  },
  added: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  aid: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  bvid: {
    type: DataTypes.CHAR(10),
    allowNull: false,
  },
  videos: {
    type: DataTypes.INTEGER,
  },
  tid: {
    type: DataTypes.INTEGER,
  },
  tname: {
    type: DataTypes.CHAR(30),
  },
  copyright: {
    type: DataTypes.INTEGER
  },
  pic: {
    type: DataTypes.CHAR(200),
  },
  title: {
    type: DataTypes.CHAR(200),
    allowNull: false,
  },
  pubdate: {
    type: DataTypes.INTEGER,
  },
  desc: {
    type: DataTypes.TEXT,
  },
  tags: {
    type: DataTypes.CHAR(500),
  },
  // TODO assosicate with tdd_member
  mid: {
    type: DataTypes.INTEGER,
  },
  code: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  attribute: {
    type: DataTypes.INTEGER,
  },
  state: {
    type: DataTypes.INTEGER,
  },
  forward: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  hasstaff: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  singer: {
    type: DataTypes.CHAR(200),
    allowNull: false,
  },
  solo: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  original: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  employed: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  isvc: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  engine: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  freq: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  activity: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  recent: {
    type: DataTypes.TINYINT,
    allowNull: false,
  },
  // TODO assosicate with tdd_video_record
  laststat: {
    type: DataTypes.BIGINT,
  },
}, {
  sequelize: db,
  tableName: 'tdd_video',
  timestamps: false,
});

module.exports = Video;
