import { MainLayout, RequiredAuth } from '@/components/layout';
import React, { FC, ReactNode } from 'react';
type Props = {
  children: ReactNode;
};
const AuthLayout: FC<Props> = ({ children }) => {
  return (
    <RequiredAuth>
      <MainLayout>{children}</MainLayout>
    </RequiredAuth>
  );
};

export default AuthLayout;
