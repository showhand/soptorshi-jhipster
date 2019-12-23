/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialWorkOrderDetailsDeleteDialogComponent } from 'app/entities/commercial-work-order-details/commercial-work-order-details-delete-dialog.component';
import { CommercialWorkOrderDetailsService } from 'app/entities/commercial-work-order-details/commercial-work-order-details.service';

describe('Component Tests', () => {
    describe('CommercialWorkOrderDetails Management Delete Component', () => {
        let comp: CommercialWorkOrderDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialWorkOrderDetailsDeleteDialogComponent>;
        let service: CommercialWorkOrderDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialWorkOrderDetailsDeleteDialogComponent]
            })
                .overrideTemplate(CommercialWorkOrderDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialWorkOrderDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialWorkOrderDetailsService);
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
