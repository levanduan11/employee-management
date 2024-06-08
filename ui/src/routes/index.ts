const apiRoutes = {
  login: 'api/v1/auth',
  activate_registration: 'api/v1/users/activate',
  profile: 'api/v1/users/profile',
  profile_photo: 'api/v1/users/profile/photo',
  password: 'api/v1/users/password',
};
const routes = {
  dashboard: '/',
  login: '/login',
  profile: '/profile',
  activate_success: '/activate/success',
  access_invalid: '/errors/access-invalid',
};
export { apiRoutes, routes };
