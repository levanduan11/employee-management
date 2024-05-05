import React, { FC, ReactNode } from 'react';
import Navbar from './Navbar';
import Sidebar from './Sidebar';
import Main from './Main';

type Props = {
  children: ReactNode;
};

const MainLayout: FC<Props> = ({ children }) => {
  return (
    <div>
      <Navbar />
      <Sidebar />
      <Main>{children}</Main>
    </div>
  );
};

export default MainLayout;
