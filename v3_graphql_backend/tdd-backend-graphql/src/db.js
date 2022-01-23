const { Sequelize } = require('sequelize');

const db = new Sequelize('tdd', 'username', 'password', {
  host: 'localhost',
  dialect: 'mysql',
});

module.exports = db;
