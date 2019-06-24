/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { BillDeleteDialogComponent } from 'app/entities/bill/bill-delete-dialog.component';
import { BillService } from 'app/entities/bill/bill.service';

describe('Component Tests', () => {
    describe('Bill Management Delete Component', () => {
        let comp: BillDeleteDialogComponent;
        let fixture: ComponentFixture<BillDeleteDialogComponent>;
        let service: BillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [BillDeleteDialogComponent]
            })
                .overrideTemplate(BillDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillService);
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
