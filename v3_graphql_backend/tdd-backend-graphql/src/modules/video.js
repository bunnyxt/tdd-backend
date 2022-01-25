import { gql } from 'apollo-server-express';

const Video = require('../models/Video');

export const typeDefs = gql`
  type Video {
    id: Int
    added: Int
    aid: Int
    bvid: String
    videos: Int
    tid: Int
    tname: String
    copyright: Int
    pic: String
    title: String
    pubdate: Int
    desc: String
    tags: String
    mid: Int
    code: Int
    attribute: Int
    state: Int
    forward: Int
    hasstaff: Int
    singer: String
    solo: Int
    original: Int
    employed: Int
    isvc: Int
    engine: Int
    freq: Int
    activity: Int
    recent: Int
    laststat: Int
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
