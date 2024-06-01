'use client';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import { useMutation, useQuery } from '@tanstack/react-query';
import { userService } from '@/service';
import { LoadingSkeleton, Photo } from '@/components';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm, useWatch } from 'react-hook-form';
import { Input } from '@/components/ui/input';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import {
  PhotoSchema,
  ProfileSchema,
  TUpdatePhoto,
  TUpdateProfile,
} from '@/schema';
import { UpdatePhotoDialog } from './_components';

const Profile = () => {
  const [previewImageUrl, setPreviewImageUrl] = useState<string>('');
  const [isOpenModalUpdatePhoto, setIsOpenModalUpdatePhoto] = useState(false);
  const form = useForm<TUpdateProfile>({
    resolver: zodResolver(ProfileSchema),
    mode: 'onBlur',
    defaultValues: {
      email: '',
      first_name: '',
      last_name: '',
    },
  });
  const updatePhotoForm = useForm<TUpdatePhoto>({
    mode: 'onChange',
    resolver: zodResolver(PhotoSchema),
  });
  const getProfile = useQuery({
    queryKey: ['get-user-profile'],
    queryFn: () => userService.getUserProfile(),
    retry: false,
  });
  const updateProfile = useMutation({
    mutationKey: ['update-profile'],
    mutationFn: (data: TUpdateProfile) => userService.updateProfile(data),
    onSuccess: () => {
      getProfile.refetch();
    },
    onError: () => {
      if (getProfile.data?.data) {
        form.reset(getProfile.data.data);
      }
    },
  });
  useEffect(() => {
    if (getProfile.data?.data) {
      form.reset(getProfile.data.data);
    }
  }, [form, getProfile.data?.data]);

  const handleUpdateProfile = (data: TUpdateProfile) => {
    updateProfile.mutate(data);
  };

  const imageFile = useWatch({
    control: updatePhotoForm.control,
    name: 'image_url',
  });

  useEffect(() => {
    if (imageFile && imageFile[0]) {
      setPreviewImageUrl(URL.createObjectURL(imageFile[0]));
    } else {
      setPreviewImageUrl('');
    }
  }, [imageFile]);
  console.log({ imageFile });

  const handleUpdatePhotoClose = () => {
    setPreviewImageUrl('');
    updatePhotoForm.reset({ image_url: undefined });
    setIsOpenModalUpdatePhoto(false);
  };
  const handleUpdatePhoto = async () => {
    const isValid = await updatePhotoForm.trigger();
    if (!isValid) return;
    setIsOpenModalUpdatePhoto(false);
  };

  if (getProfile.isLoading || getProfile.isFetching) {
    return <LoadingSkeleton />;
  }
  return (
    <div>
      <div className="grid grid-cols-12 gap-3">
        <div className="col-span-3">
          <Card className="p-4">
            <CardContent className="flex items-center justify-center">
              <Photo src={getProfile.data?.data?.imageUrl} />
            </CardContent>
            <CardFooter className="justify-center">
              <UpdatePhotoDialog
                open={isOpenModalUpdatePhoto}
                onOpenChange={setIsOpenModalUpdatePhoto}
                imageUrl={previewImageUrl}
                form={updatePhotoForm}
                onClose={handleUpdatePhotoClose}
                onUpdatePhoto={handleUpdatePhoto}
              />
            </CardFooter>
          </Card>
        </div>
        <div className="col-span-8">
          <Form {...form}>
            <form onSubmit={form.handleSubmit(handleUpdateProfile)}>
              <Card>
                <CardHeader>
                  <CardTitle className="text-sm">Profile Settings</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="mb-3">
                    <p className="text-sm font-bold mb-1">User Name</p>
                    <div className="bg-slate-100 p-3 rounded-md">
                      <span>{getProfile.data?.data?.username}</span>
                    </div>
                  </div>
                  <FormField
                    control={form.control}
                    name="email"
                    render={() => (
                      <FormItem>
                        <FormLabel>Email</FormLabel>
                        <FormControl>
                          <Input {...form.register('email')} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="first_name"
                    render={() => (
                      <FormItem>
                        <FormLabel>First Name</FormLabel>
                        <FormControl>
                          <Input {...form.register('first_name')} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="last_name"
                    render={() => (
                      <FormItem>
                        <FormLabel>Last Name</FormLabel>
                        <FormControl>
                          <Input {...form.register('last_name')} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </CardContent>
                <CardFooter className="justify-center gap-3">
                  <Button>Update Profile</Button>
                  <Button variant="outline" asChild>
                    <Link href="/profile/update-password">Update Password</Link>
                  </Button>
                </CardFooter>
              </Card>
            </form>
          </Form>
        </div>
      </div>
    </div>
  );
};

export default Profile;
