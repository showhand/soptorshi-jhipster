/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { DesignationWiseAllowanceDeleteDialogComponent } from 'app/entities/designation-wise-allowance/designation-wise-allowance-delete-dialog.component';
import { DesignationWiseAllowanceService } from 'app/entities/designation-wise-allowance/designation-wise-allowance.service';

describe('Component Tests', () => {
    describe('DesignationWiseAllowance Management Delete Component', () => {
        let comp: DesignationWiseAllowanceDeleteDialogComponent;
        let fixture: ComponentFixture<DesignationWiseAllowanceDeleteDialogComponent>;
        let service: DesignationWiseAllowanceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DesignationWiseAllowanceDeleteDialogComponent]
            })
                .overrideTemplate(DesignationWiseAllowanceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DesignationWiseAllowanceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignationWiseAllowanceService);
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
