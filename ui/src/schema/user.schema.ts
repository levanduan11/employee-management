import { z } from 'zod';
export const ProfileSchema = z.object({
  email: z.string().trim().min(1, 'Email is required').email('Invalid email'),
  first_name: z.string().trim().min(1, 'First name is required'),
  last_name: z.string().trim().min(1, 'Last name is required'),
});

const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
const sizeInMB = (sizeInBytes: number, decimalsNum = 2) => {
  const result = sizeInBytes / (1024 * 1024);
  return +result.toFixed(decimalsNum);
};

export const PhotoSchema = z.object({
  image_url: z
    .custom<FileList>()
    .refine((file) => file && file.length !== 0, {
      message: 'Please select a file',
    })
    .refine((file) => file && file[0] && file[0].size <= MAX_FILE_SIZE, {
      message: `Max file size is ${sizeInMB(MAX_FILE_SIZE)}MB`,
    }),
});

export const UpdatePasswordSchema = z
  .object({
    old_password: z.string().trim().min(1, 'Old password is required'),
    new_password: z.string().trim().min(1, 'New password is required'),
    confirm_password: z.string().trim().min(1, 'Confirm password is required'),
  })
  .refine((data) => data.new_password === data.confirm_password, {
    message: 'Password does not match',
    path: ['confirm_password'],
  })
  .refine((data) => data.old_password !== data.new_password, {
    message: 'New password cannot be the same as old password',
    path: ['new_password'],
  });

export type TUpdateProfile = z.infer<typeof ProfileSchema>;
export type TUpdatePhoto = z.infer<typeof PhotoSchema>;
export type TUpdatePassword = z.infer<typeof UpdatePasswordSchema>;
