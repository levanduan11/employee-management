import React, { FC, ReactNode } from 'react';

type Props = {
  children: ReactNode;
};

const ErrorLayout: FC<Props> = ({ children }) => {
  return <div>{children}</div>;
};

export default ErrorLayout;
