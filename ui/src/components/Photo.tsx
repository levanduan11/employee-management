import { cn } from '@/lib/utils';
import Image from 'next/image';
import React, { FC } from 'react';

type Props = {
  className?: string;
  width?: number;
  height?: number;
  alt?: string;
  src?: string;
};
const defaultPhoto = '/avatar-default.png';
const Photo: FC<Props> = ({ className, src, width = 150, height = 150 }) => {
  return (
    <Image
      className={cn('rounded-md', className)}
      src={src || defaultPhoto}
      alt="Photo"
      width={width}
      height={height}
      priority
    />
  );
};

export default Photo;
