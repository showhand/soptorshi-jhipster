/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AttendanceExcelUploadDetailComponent } from 'app/entities/attendance-excel-upload/attendance-excel-upload-detail.component';
import { AttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';

describe('Component Tests', () => {
    describe('AttendanceExcelUpload Management Detail Component', () => {
        let comp: AttendanceExcelUploadDetailComponent;
        let fixture: ComponentFixture<AttendanceExcelUploadDetailComponent>;
        const route = ({ data: of({ attendanceExcelUpload: new AttendanceExcelUpload(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AttendanceExcelUploadDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AttendanceExcelUploadDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AttendanceExcelUploadDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.attendanceExcelUpload).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
