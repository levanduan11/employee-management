const apiRoutes = {
  login: 'api/v1/auth',
  activate_registration: 'api/v1/users/activate',
  profile:'api/v1/users/profile'
};
const routes = {
  dashboard: '/',
  login: '/login',
  profile: '/profile',
  activate_success: '/activate/success',
  access_invalid: '/errors/access-invalid',
};
export { apiRoutes, routes };
