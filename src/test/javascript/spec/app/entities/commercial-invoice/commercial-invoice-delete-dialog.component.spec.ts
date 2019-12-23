/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialInvoiceDeleteDialogComponent } from 'app/entities/commercial-invoice/commercial-invoice-delete-dialog.component';
import { CommercialInvoiceService } from 'app/entities/commercial-invoice/commercial-invoice.service';

describe('Component Tests', () => {
    describe('CommercialInvoice Management Delete Component', () => {
        let comp: CommercialInvoiceDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialInvoiceDeleteDialogComponent>;
        let service: CommercialInvoiceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialInvoiceDeleteDialogComponent]
            })
                .overrideTemplate(CommercialInvoiceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialInvoiceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialInvoiceService);
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
