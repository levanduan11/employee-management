import { CircleGauge, Settings, UsersRound } from 'lucide-react';
import Link from 'next/link';
import React from 'react';

const Sidebar = () => {
  return (
    <div className="fixed top-0 left-0 bottom-0 w-[230px] bg-slate-900">
      <h2 className="bg-slate-700 text-center border-b-[3px] border-b-lime-900 text-slate-300 font-serif pt-4 pb-4">
        Employee Management
      </h2>
      <ul className="mt-4">
        <li className="text-gray-400 flex gap-3 hover:bg-slate-400 hover:text-white p-3">
          <CircleGauge />
          <Link href="/">Dashboard</Link>
        </li>
        <li className="text-gray-400 flex gap-3 hover:bg-slate-400 hover:text-white p-3">
          <UsersRound />
          <Link href="#">Employees</Link>
        </li>
        <li className="text-gray-400 flex gap-3 hover:bg-slate-400 hover:text-white p-3">
          <Settings />
          <Link href="#">Settings</Link>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
