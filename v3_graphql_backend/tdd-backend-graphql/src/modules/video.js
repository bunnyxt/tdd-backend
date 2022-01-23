import { gql } from 'apollo-server-express';

const Video = require('../models/video');

export const typeDefs = gql`
  type Video {
    id: Int
    added: Int
    aid: Int
    bvid: String
    title: String
  }

  extend type Query {
    video(aid: Int!): Video
  }
`;

export const resolvers = {
  Query: {
    video: async (obj, args, context, info) => {
      const { aid } = args;
      return Video.findOne({
        where: { aid },
      });
    },
  },
};
