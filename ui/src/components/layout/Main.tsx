import React, { FC, ReactNode } from 'react';

type Props = {
  children: ReactNode;
  className?: string;
};

const Main: FC<Props> = ({ className, children }) => {
  return <div className={`${className} p-3 md:ml-[230px]`}>{children}</div>;
};

export default Main;
