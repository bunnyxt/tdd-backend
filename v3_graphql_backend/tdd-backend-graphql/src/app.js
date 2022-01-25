require('dotenv').config()
const express = require('express');
const http = require('http');
const { ApolloServer } = require('apollo-server-express');
const { ApolloServerPluginDrainHttpServer } = require('apollo-server-core');
const modules = require('./modules/modules');

async function startApolloServer() {
  const app = express();
  const httpServer = http.createServer(app);
  const server = new ApolloServer({
    modules,
    plugins: [ApolloServerPluginDrainHttpServer({ httpServer })],
  });

  await server.start();
  server.applyMiddleware({ app });
  // eslint-disable-next-line no-promise-executor-return
  await new Promise((resolve) => httpServer.listen({ port: process.env.PORT }, resolve));
  console.log(`🚀 Server ready at http://localhost:${process.env.PORT}${server.graphqlPath}`);
}

startApolloServer();
