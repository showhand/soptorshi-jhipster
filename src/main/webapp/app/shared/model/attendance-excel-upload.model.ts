import { Moment } from 'moment';
import { IAttendance } from 'app/shared/model/attendance.model';

export const enum AttendanceType {
    FINGER = 'FINGER',
    FACE = 'FACE'
}

export interface IAttendanceExcelUpload {
    id?: number;
    fileContentType?: string;
    file?: any;
    type?: AttendanceType;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    attendances?: IAttendance[];
}

export class AttendanceExcelUpload implements IAttendanceExcelUpload {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public type?: AttendanceType,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public attendances?: IAttendance[]
    ) {}
}
