'use client';
import React from 'react';
import { useForm } from 'react-hook-form';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { zodResolver } from '@hookform/resolvers/zod';
import { LoginSchema, TLogin } from '@/schema';
import { useMutation } from '@tanstack/react-query';
import { authService } from '@/service';

const Login = () => {
  const form = useForm<TLogin>({
    resolver: zodResolver(LoginSchema),
    mode: 'onBlur',
    defaultValues: {
      username: '',
      password: '',
    },
  });

  const loginMutation = useMutation({
    mutationKey: ['login'],
    mutationFn: (data: TLogin) => authService.login(data),
    onSuccess: (data) => {
      authService.handleLoginSuccess(data.data);
    },
  });
  function onSubmit(values: TLogin) {
    loginMutation.mutate(values);
  }

  return (
    <div className="container flex h-screen justify-center pt-40 m-auto bg-slate-200">
      <div className="w-[500px]">
        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(onSubmit)}
            className="shadow-md bg-white pt-4 pb-3 ps-3 pe-3 rounded space-y-8"
          >
            <h2 className="text-center font-bold">Login</h2>
            <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Username</FormLabel>
                  <FormControl>
                    <Input placeholder="username" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input placeholder="password" {...field} type="password" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit">Login</Button>
          </form>
        </Form>
      </div>
    </div>
  );
};

export default Login;
