'use client';
import { routes } from '@/routes';
import { authService } from '@/service';
import { useRouter } from 'next/navigation';
import React, { FC, ReactNode, useEffect, useState } from 'react';
import LoadingSkeleton from '../LoadingSkeleton';

type Props = {
  children: ReactNode;
};
const RequiredAuth: FC<Props> = ({ children }) => {
  const isLoggedIn = authService.isLoggedIn();
  const router = useRouter();
  const [isCheckedLoggedIn, setIsCheckedLoggedIn] = useState(true);

  useEffect(() => {
    if (!isLoggedIn) {
      router.replace(routes.login);
    } else {
      setIsCheckedLoggedIn(false);
    }
  }, [isLoggedIn, router]);

  if (isCheckedLoggedIn) {
    return <LoadingSkeleton count={5} />;
  } else {
    return <>{children}</>;
  }
};

export default RequiredAuth;
