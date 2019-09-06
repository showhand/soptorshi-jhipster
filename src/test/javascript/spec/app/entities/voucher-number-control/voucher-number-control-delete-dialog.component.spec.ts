/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { VoucherNumberControlDeleteDialogComponent } from 'app/entities/voucher-number-control/voucher-number-control-delete-dialog.component';
import { VoucherNumberControlService } from 'app/entities/voucher-number-control/voucher-number-control.service';

describe('Component Tests', () => {
    describe('VoucherNumberControl Management Delete Component', () => {
        let comp: VoucherNumberControlDeleteDialogComponent;
        let fixture: ComponentFixture<VoucherNumberControlDeleteDialogComponent>;
        let service: VoucherNumberControlService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [VoucherNumberControlDeleteDialogComponent]
            })
                .overrideTemplate(VoucherNumberControlDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VoucherNumberControlDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherNumberControlService);
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
