import { gql } from 'apollo-server-express';

const VideoRecord = require('../models/VideoRecord');

export const typeDefs = gql`
  type VideoRecord {
    id: Int
    added: Int
    aid: Int
    view: Int
    danmaku: Int
    reply: Int
    favorite: Int
    coin: Int
    share: Int
    like: Int
  }

  extend type Query {
    videoRecordsByAid(aid: Int!): [VideoRecord]
  }
`;

export const resolvers = {
  Query: {
    videoRecordsByAid: async (obj, args, context, info) => {
      const { aid } = args;
      return VideoRecord.findAll({
        where: { aid },
      });
    },
  },
};
