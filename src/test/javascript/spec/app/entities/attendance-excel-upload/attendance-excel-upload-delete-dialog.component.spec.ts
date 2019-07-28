/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { AttendanceExcelUploadDeleteDialogComponent } from 'app/entities/attendance-excel-upload/attendance-excel-upload-delete-dialog.component';
import { AttendanceExcelUploadService } from 'app/entities/attendance-excel-upload/attendance-excel-upload.service';

describe('Component Tests', () => {
    describe('AttendanceExcelUpload Management Delete Component', () => {
        let comp: AttendanceExcelUploadDeleteDialogComponent;
        let fixture: ComponentFixture<AttendanceExcelUploadDeleteDialogComponent>;
        let service: AttendanceExcelUploadService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AttendanceExcelUploadDeleteDialogComponent]
            })
                .overrideTemplate(AttendanceExcelUploadDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AttendanceExcelUploadDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendanceExcelUploadService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
