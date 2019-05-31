/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { PayrollManagementDeleteDialogComponent } from 'app/entities/payroll-management/payroll-management-delete-dialog.component';
import { PayrollManagementService } from 'app/entities/payroll-management/payroll-management.service';

describe('Component Tests', () => {
    describe('PayrollManagement Management Delete Component', () => {
        let comp: PayrollManagementDeleteDialogComponent;
        let fixture: ComponentFixture<PayrollManagementDeleteDialogComponent>;
        let service: PayrollManagementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PayrollManagementDeleteDialogComponent]
            })
                .overrideTemplate(PayrollManagementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PayrollManagementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollManagementService);
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
