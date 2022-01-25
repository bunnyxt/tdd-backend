const { DataTypes, Model } = require('sequelize');
const db = require('../db');

class VideoRecord extends Model {}

VideoRecord.init({
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
  view: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  danmaku: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  reply: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  favorite: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  coin: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  share: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  like: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
}, {
  sequelize: db,
  tableName: 'tdd_video_record',
  timestamps: false,
});

module.exports = VideoRecord;
