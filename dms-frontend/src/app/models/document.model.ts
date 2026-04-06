export interface DocumentModel {
  id: number
  url?: string
  title: string
  description: string
  type: string
  size: number
  uploadDate: string
  fileKey?: string
  recognizedText?: string
  // fileData: string
}
