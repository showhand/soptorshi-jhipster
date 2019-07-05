/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionDetailsDeleteDialogComponent } from 'app/entities/requisition-details/requisition-details-delete-dialog.component';
import { RequisitionDetailsService } from 'app/entities/requisition-details/requisition-details.service';

describe('Component Tests', () => {
    describe('RequisitionDetails Management Delete Component', () => {
        let comp: RequisitionDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<RequisitionDetailsDeleteDialogComponent>;
        let service: RequisitionDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionDetailsDeleteDialogComponent]
            })
                .overrideTemplate(RequisitionDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequisitionDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequisitionDetailsService);
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
