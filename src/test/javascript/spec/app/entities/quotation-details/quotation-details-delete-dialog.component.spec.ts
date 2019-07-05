/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { QuotationDetailsDeleteDialogComponent } from 'app/entities/quotation-details/quotation-details-delete-dialog.component';
import { QuotationDetailsService } from 'app/entities/quotation-details/quotation-details.service';

describe('Component Tests', () => {
    describe('QuotationDetails Management Delete Component', () => {
        let comp: QuotationDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<QuotationDetailsDeleteDialogComponent>;
        let service: QuotationDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [QuotationDetailsDeleteDialogComponent]
            })
                .overrideTemplate(QuotationDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuotationDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotationDetailsService);
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
