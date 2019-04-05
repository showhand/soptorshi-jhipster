import { IAttachment } from 'app/shared/model/attachment.model';

export interface IAcademicInformation {
    id?: number;
    degree?: string;
    boardOrUniversity?: string;
    passingYear?: number;
    group?: string;
    attachments?: IAttachment[];
    employeeId?: number;
}

export class AcademicInformation implements IAcademicInformation {
    constructor(
        public id?: number,
        public degree?: string,
        public boardOrUniversity?: string,
        public passingYear?: number,
        public group?: string,
        public attachments?: IAttachment[],
        public employeeId?: number
    ) {}
}
