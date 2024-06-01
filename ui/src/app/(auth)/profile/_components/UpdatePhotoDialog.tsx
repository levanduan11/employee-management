import { Photo } from '@/components';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { TUpdatePhoto } from '@/schema';
import { DialogProps } from '@radix-ui/react-dialog';
import { FC } from 'react';
import { UseFormReturn } from 'react-hook-form';

type Props = DialogProps & {
  imageUrl: string;
  form: UseFormReturn<TUpdatePhoto, unknown, undefined>;
  onClose: () => void;
  onUpdatePhoto: (e: unknown) => void;
};

const UpdatePhotoDialog: FC<Props> = ({
  imageUrl,
  form,
  onClose,
  onUpdatePhoto,
  ...restProps
}) => {
  return (
    <Dialog {...restProps}>
      <DialogTrigger asChild>
        <Button variant="outline">Change photo</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Update Photo</DialogTitle>
        </DialogHeader>
        <div>
          <div className="flex justify-center">
            <Photo width={110} height={110} src={imageUrl} />
          </div>
          <div className="text-center my-5">
            <input
              id="image-url"
              accept="image/*"
              type="file"
              {...form.register('image_url')}
            />
            <p className="my-2 text-red-500">
              {form.formState.errors.image_url?.message}
            </p>
          </div>
        </div>
        <DialogFooter>
          <Button type="button" onClick={(e) => onUpdatePhoto(e)}>
            Update photo
          </Button>
          <Button type="button" variant="secondary" onClick={onClose}>
            Cancel
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default UpdatePhotoDialog;
