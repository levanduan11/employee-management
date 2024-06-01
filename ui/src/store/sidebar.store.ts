import { create } from 'zustand';

type SidebarState = {
  isOpen: boolean;
};

type SidebarAction = {
  open: () => void;
  close: () => void;
};

export const useSidebarStore = create<SidebarState & SidebarAction>((set) => ({
  isOpen: false,
  open: () => set({ isOpen: true }),
  close: () => set({ isOpen: false }),
}));
