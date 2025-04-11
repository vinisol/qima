export interface Category {
  id: number;
  name: string;
  categoryPath: string;
  parentId: number | null;
  createdAt: string;
  updatedAt: string;
}
