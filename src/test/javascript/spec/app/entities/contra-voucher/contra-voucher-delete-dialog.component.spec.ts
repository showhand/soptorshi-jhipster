/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { ContraVoucherDeleteDialogComponent } from 'app/entities/contra-voucher/contra-voucher-delete-dialog.component';
import { ContraVoucherService } from 'app/entities/contra-voucher/contra-voucher.service';

describe('Component Tests', () => {
    describe('ContraVoucher Management Delete Component', () => {
        let comp: ContraVoucherDeleteDialogComponent;
        let fixture: ComponentFixture<ContraVoucherDeleteDialogComponent>;
        let service: ContraVoucherService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ContraVoucherDeleteDialogComponent]
            })
                .overrideTemplate(ContraVoucherDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContraVoucherDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContraVoucherService);
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
