/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyChallanDeleteDialogComponent } from 'app/entities/supply-challan/supply-challan-delete-dialog.component';
import { SupplyChallanService } from 'app/entities/supply-challan/supply-challan.service';

describe('Component Tests', () => {
    describe('SupplyChallan Management Delete Component', () => {
        let comp: SupplyChallanDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyChallanDeleteDialogComponent>;
        let service: SupplyChallanService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyChallanDeleteDialogComponent]
            })
                .overrideTemplate(SupplyChallanDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyChallanDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyChallanService);
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
