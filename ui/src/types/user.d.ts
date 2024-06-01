interface UserProfile {
  username: string;
  email: string;
  firstName?: string;
  lastName?: string;
  imageUrl?: string;
  roles: Array<string>;
}
