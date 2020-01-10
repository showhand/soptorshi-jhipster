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
    attendances?: IAttendance[];
}

export class AttendanceExcelUpload implements IAttendanceExcelUpload {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public type?: AttendanceType,
        public attendances?: IAttendance[]
    ) {}
}
