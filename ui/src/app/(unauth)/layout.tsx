import React, { FC, ReactNode } from 'react';
type Props = {
  children: ReactNode;
};
const UnAuthLayout: FC<Props> = ({ children }) => {
  return <>{children}</>;
};

export default UnAuthLayout;
