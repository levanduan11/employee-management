import { LoadingSkeleton } from '@/components';
import { FC } from 'react';

const Loading: FC = () => {
  return <LoadingSkeleton count={5} />;
};
export default Loading;
