import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import Link from 'next/link';
import React from 'react';

const ActiveRegistionSuccess = () => {
  return (
    <main className="container flex h-screen justify-center items-center bg-lime-50">
      <div className="text-center">
        <Card>
          <CardHeader>
            <CardTitle>Account Activation</CardTitle>
          </CardHeader>
          <CardContent>
            <h3 className="text-slate-400">Your account has been activated</h3>
          </CardContent>
          <CardFooter className="text-center">
            <Button className="text-center w-full" asChild>
              <Link href="/login">Go to Login</Link>
            </Button>
          </CardFooter>
        </Card>
      </div>
    </main>
  );
};

export default ActiveRegistionSuccess;
