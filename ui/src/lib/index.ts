const ACTIVE_PROFILE = process.env.NEXT_PUBLIC_ACTIVE_PROFILE;

export const isDev = () => ACTIVE_PROFILE === 'dev';

export const isProd = () => ACTIVE_PROFILE === 'prod';
