import React from 'react';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';

const User = () => {
  return (
    <div>
      <Card className="rounded-[0px]">
        <CardHeader>
          <CardTitle>User Management</CardTitle>
          <CardDescription>User{"(0)"}</CardDescription>
        </CardHeader>
        <CardContent>
          <p>Card Content</p>
        </CardContent>
      </Card>
    </div>
  );
};

export default User;
