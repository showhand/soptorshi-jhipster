/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { LeaveApplicationDeleteDialogComponent } from 'app/entities/leave-application/leave-application-delete-dialog.component';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';

describe('Component Tests', () => {
    describe('LeaveApplication Management Delete Component', () => {
        let comp: LeaveApplicationDeleteDialogComponent;
        let fixture: ComponentFixture<LeaveApplicationDeleteDialogComponent>;
        let service: LeaveApplicationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LeaveApplicationDeleteDialogComponent]
            })
                .overrideTemplate(LeaveApplicationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LeaveApplicationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LeaveApplicationService);
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
