interface UserProfile {
  username: string;
  email: string;
  first_name?: string;
  last_name?: string;
  image_url?: string;
  roles: Array<string>;
}
