'use client';

import { LoadingSkeleton } from '@/components';
import { routes } from '@/routes';
import { userService } from '@/service';
import { useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import React, { FC, useEffect } from 'react';

type ActivateProps = {
  params: {
    token: string;
  };
};
const Activate: FC<ActivateProps> = ({ params: { token } }) => {
  const router = useRouter();
  const activation = useQuery({
    queryKey: ['users', 'activate-registration', token],
    queryFn: () => userService.activateRegistration(token),
    enabled: !!token,
    retry: false,
  });

  useEffect(() => {
    if (activation.isSuccess) {
      router.replace(routes.activate_success);
    }
    if (activation.isError) {
      router.replace(routes.access_invalid);
    }
  }, [activation.isError, activation.isSuccess, router]);
  return <LoadingSkeleton count={1} />;
};

export default Activate;
