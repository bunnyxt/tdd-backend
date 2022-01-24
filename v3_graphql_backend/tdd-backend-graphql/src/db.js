const { Sequelize } = require('sequelize');
require('dotenv').config()

const db = new Sequelize('tdd', process.env.DB_USERNAME, process.env.DB_PASSWORD, {
  host: process.env.DB_HOST,
  dialect: 'mysql',
});

module.exports = db;
