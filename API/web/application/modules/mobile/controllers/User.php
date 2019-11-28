<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class User extends MX_Controller {

	public function __construct() 
	{
        parent::__construct();
		
		//$this->auth->adminRestrict();
		//$this->load->library('datatables');
	}
	


	function getProfile()
	{
		$id_user = $this->input->post('id_user');	

//		$token_data = $thus->auth->tokenAuth($this->input->post('token'));
		if(empty($id_user))
		{
			$output = array(
					'meta' => array(
										"code" => 201,
										"message" => "Id User tidak boleh kosong!",
									)
				);

		} else {
			
					$data = $this->datamapper->query2("SELECT * FROM tb_users_mobile WHERE id_user_mobile = '$id_user'");
					$output = array(
					'meta' => array(
										"code" => 200,
										"message" => "Berhasil"
									),
					"data" => $data,
								
					);
			}
		print_r(json_encode($output));
			
		}

	function GantiPassword()
	{
		$id_user = $this->input->post('id_user');	
		$password = $this->input->post('password');	
		$konfirmasi_password = $this->input->post('konfirmasi_password');	
	
		if(empty($id_user))
		{
					$output = array(
							'meta' => array(
												"code" => 201,
												"message" => "Id User tidak boleh kosong!",
											)
						);

		} else if(empty($password)){
				$output = array(
					'meta' => array(
										"code" => 201,
										"message" => "Password wajib diisi...!!",
									)
				);
			} else if(empty($konfirmasi_password)){
				$output = array(
					'meta' => array(
										"code" => 201,
										"message" => "Konfirmasi Password wajib diisi...!!",
									)
				);
			} else if($password <> $konfirmasi_password){
				$output = array(
					'meta' => array(
										"code" => 201,
										"message" => "Konfirmasi password tidak sama...!!",
									)
				);
			}else{
					$this->db->query("UPDATE tb_users_mobile set password = MD5('$password') WHERE id_user_mobile = '$id_user'");
					$output = array(
					'meta' => array(
										"code" => 200,
										"message" => "Password berhasil diupdate..."
									)
								
					);
			}
				print_r(json_encode($output));
		}
		
		function uploadFoto()
	{
		$id_user_mobile	= $this->input->post("id_user_mobile");
		// $foto 			= $this->input->post('foto');
		$tgl_input 		= date("Y-m-d H:i:s");
		$tgl_update 	= date("Y-m-d H:i:s");
		
		if(empty($id_user_mobile)){
			$output = array(
						'meta' => array(
											"code" 		=> 201,
											"message" 	=> "Lengkapi Data Profil Anda!"
										)
					);
				print_r(json_encode($output));
			return($output);
		}
		
		if(!empty($_FILES['foto']['name']))
			{
				$do_upload1 = $this->utilities->uploadGambar('foto', 'assets/images/user_mobile', 'jpg', 512000, $this->utilities->keyHash());
				if($do_upload1['status'] == FALSE)
					{
						$error_message = $do_upload1['message'] ;
						$output = array(
								'meta' => array(
													"code" => 201,
													"message" => 'Gagal Update Data Profil, Error Message : '.$error_message.''
												),
								'data' => ''
						);
					print_r(json_encode($output));
					}
					else
					{
						$upload1 = $do_upload1['filename'];
					}
			}else
				{
					if(!empty($id_user_mobile))
					{	
						$upload1 = $this->datamapper->rowData('tb_users_mobile', 'foto', 'id_user_mobile', $id_user_mobile);
					}else
						{
							$upload1 = '';
						}
				}
				

				$data = array(
						'id_user_mobile'	=> $id_user_mobile,
						'foto' 			=> $upload1
				);
				$this->datamapper->update('tb_users_mobile', 'id_user_mobile', $id_user_mobile, $data);
				
				$output = array(
						'meta' => array(
											"code" 		=> 200,
											"message" 	=> "Profil Berhasil Diupdate"
										)
					);
				print_r(json_encode($output));
		}
		
		
	function updateProfile(){
	
		$id_user_mobile		= $this->input->post("id_user_mobile");
		$nama				= $this->input->post("nama");
		$username			= $this->input->post("username");
		$no_hp				= $this->input->post("no_hp");
		$no_hp_lama			= $this->input->post("no_hp_lama");
		$alamat				= $this->input->post("alamat");
		
		if(empty($nama) || empty($username) || empty($alamat) || empty($no_hp)){
			$output = array(
						'meta' => array(
											"code" 		=> 201,
											"message" 	=> "Lengkapi Data Diri Anda!"
										)
					);
				print_r(json_encode($output));
			return($output);
		}
		
		if($no_hp_lama === $no_hp){

			$this->db->query("UPDATE tb_users_mobile set nama = '$nama' ,no_hp = '$no_hp', username = '$username'  , alamat = '$alamat' WHERE id_user_mobile = '$id_user_mobile'");

			$output = array(
							'meta' => array(
												"code" 		=> 200,
												"message" 	=> "Profil Berhasil Diupdate"
											)
						);
						print_r(json_encode($output));
			
		}else{

				$query_no_hp 	= $this->db->query("SELECT no_hp FROM tb_users_mobile WHERE no_hp = '$no_hp'"); 
				
				if($query_no_hp){
				
				if($query_no_hp->num_rows() > 0){
					$output = array(
						'meta' => array(
											"code" 		=> 201,
											"message" 	=> "No HP Sudah Terdaftar!"
										)
					);
					print_r(json_encode($output));

				}else{
				
					$this->db->query("UPDATE tb_users_mobile set nama = '$nama' ,no_hp = '$no_hp', username = '$username'  , alamat = '$alamat' WHERE id_user_mobile = '$id_user_mobile'");

						$output = array(
										'meta' => array(
															"code" 		=> 200,
															"message" 	=> "Profil Berhasil Diupdate"
														)
									);
									print_r(json_encode($output));
						}
					}
					
		}
	
	}
	

	}