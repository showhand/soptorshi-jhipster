/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AttendanceExcelUploadUpdateComponent } from 'app/entities/attendance-excel-upload/attendance-excel-upload-update.component';
import { AttendanceExcelUploadService } from 'app/entities/attendance-excel-upload/attendance-excel-upload.service';
import { AttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';

describe('Component Tests', () => {
    describe('AttendanceExcelUpload Management Update Component', () => {
        let comp: AttendanceExcelUploadUpdateComponent;
        let fixture: ComponentFixture<AttendanceExcelUploadUpdateComponent>;
        let service: AttendanceExcelUploadService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AttendanceExcelUploadUpdateComponent]
            })
                .overrideTemplate(AttendanceExcelUploadUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AttendanceExcelUploadUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendanceExcelUploadService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AttendanceExcelUpload(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.attendanceExcelUpload = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AttendanceExcelUpload();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.attendanceExcelUpload = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
