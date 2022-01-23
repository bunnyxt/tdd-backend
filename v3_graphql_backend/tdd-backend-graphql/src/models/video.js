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
  // TODO add more fields
  title: {
    type: DataTypes.CHAR(200),
    allowNull: false,
  },
}, {
  sequelize: db,
  tableName: 'tdd_video',
  timestamps: false,
});

module.exports = Video;
