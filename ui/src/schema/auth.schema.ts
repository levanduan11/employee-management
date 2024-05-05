import * as z from 'zod';

export const LoginSchema = z.object({
  username: z.string().trim().min(1, {
    message: 'Username is required',
  }),
  password: z.string().trim().min(1, {
    message: 'Password is required',
  }),
});

export type TLogin = z.infer<typeof LoginSchema>;
