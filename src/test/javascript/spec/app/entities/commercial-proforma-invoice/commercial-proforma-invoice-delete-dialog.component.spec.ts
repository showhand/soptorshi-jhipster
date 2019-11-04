/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialProformaInvoiceDeleteDialogComponent } from 'app/entities/commercial-proforma-invoice/commercial-proforma-invoice-delete-dialog.component';
import { CommercialProformaInvoiceService } from 'app/entities/commercial-proforma-invoice/commercial-proforma-invoice.service';

describe('Component Tests', () => {
    describe('CommercialProformaInvoice Management Delete Component', () => {
        let comp: CommercialProformaInvoiceDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialProformaInvoiceDeleteDialogComponent>;
        let service: CommercialProformaInvoiceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialProformaInvoiceDeleteDialogComponent]
            })
                .overrideTemplate(CommercialProformaInvoiceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialProformaInvoiceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialProformaInvoiceService);
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
