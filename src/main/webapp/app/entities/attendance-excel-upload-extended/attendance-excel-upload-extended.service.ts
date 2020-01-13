import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadService } from 'app/entities/attendance-excel-upload';

type EntityResponseType = HttpResponse<IAttendanceExcelUpload>;
type EntityArrayResponseType = HttpResponse<IAttendanceExcelUpload[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceExcelUploadExtendedService extends AttendanceExcelUploadService {
    public resourceUrl = SERVER_API_URL + 'api/extended/attendance-excel-uploads';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/attendance-excel-uploads';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
