'use client';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { TUpdatePassword, UpdatePasswordSchema } from '@/schema';
import { userService } from '@/service';
import { zodResolver } from '@hookform/resolvers/zod';
import { useMutation } from '@tanstack/react-query';
import Link from 'next/link';
import React from 'react';
import { useForm } from 'react-hook-form';

const UpdatePassword = () => {
  const defaultValues = (): TUpdatePassword => ({
    old_password: '',
    new_password: '',
    confirm_password: '',
  });
  const form = useForm<TUpdatePassword>({
    resolver: zodResolver(UpdatePasswordSchema),
    mode: 'onBlur',
    defaultValues: defaultValues(),
  });

  const updatePassword = useMutation({
    mutationKey: ['update-password'],
    mutationFn: (data: TUpdatePassword) => userService.updatePassword(data),
  });
  const handleUpdatePassword = (data: TUpdatePassword) => {
    updatePassword.mutate(data);
    form.reset(defaultValues());
  };

  return (
    <div className="container">
      <div className="flex justify-center p-4">
        <Form {...form}>
          <form onSubmit={form.handleSubmit(handleUpdatePassword)}>
            <Card className="w-[500px]">
              <CardHeader>
                <CardTitle className="text-center">Update Password</CardTitle>
              </CardHeader>
              <CardContent>
                <FormField
                  name="old_password"
                  control={form.control}
                  render={() => (
                    <FormItem>
                      <FormLabel>Old Password</FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          {...form.register('old_password')}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="new_password"
                  control={form.control}
                  render={() => (
                    <FormItem>
                      <FormLabel>New Password</FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          {...form.register('new_password')}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="confirm_password"
                  control={form.control}
                  render={() => (
                    <FormItem>
                      <FormLabel>Confirm Password</FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          {...form.register('confirm_password')}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </CardContent>
              <CardFooter>
                <Button type="submit">Update Password</Button>
                <Link className="ml-3" href="/profile">
                  <Button variant="outline">Cancel</Button>
                </Link>
              </CardFooter>
            </Card>
          </form>
        </Form>
      </div>
    </div>
  );
};

export default UpdatePassword;
